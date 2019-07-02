using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using Grossmann.Tankerapi.Client.Core;
using Grossmann.Tankerapi.Client.Details;
using Grossmann.Tankerapi.Client.Prices;
using Microsoft.FSharp.Core;
using Newtonsoft.Json;
using RestSharp.Extensions;

namespace Grossmann.Tankerapi.Client.Api.Json
{
    class Class1
    {
    }

    public class PriceJsonConverter : JsonConverter<decimal>
    {
        public override void WriteJson(JsonWriter writer, decimal value, JsonSerializer serializer)
        {
            writer.WriteValue(value.ToString(CultureInfo.InvariantCulture));
        }

        public override decimal ReadJson(JsonReader reader, Type objectType, decimal existingValue, bool hasExistingValue,
            JsonSerializer serializer)
        {
            if (decimal.TryParse(reader.Value.ToString(), out var number))
                return number;

            return -1.0m;
        }
    }

    public class PriceResponseJsonConverter : JsonConverter<PriceResponse>
    {
        public override void WriteJson(JsonWriter writer, PriceResponse value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }

        public override PriceResponse ReadJson(JsonReader reader, Type objectType, PriceResponse existingValue, bool hasExistingValue,
            JsonSerializer serializer)
        {
            var response = new PriceResponse();
            reader.Read();
            while (reader.TokenType != JsonToken.EndObject)
            {
                switch (reader.Value)
                {
                    case "ok":
                        response.Ok = reader.ReadAsBoolean().GetValueOrDefault(false);
                        break;
                    case "license":
                        response.License = reader.ReadAsString();
                        break;
                    case "data":
                        response.Data = reader.ReadAsString();
                        break;
                    case "status":
                        response.Status = reader.ReadAsString();
                        break;
                    case "prices":
                        response.Prices = reader.ReadAsPrices();
                        break;
                }

                reader.Read();
            }

            return response;
        }
    }

    public class DetailResponseJsonConverter : JsonConverter<DetailedResponse>
    {
        public override void WriteJson(JsonWriter writer, DetailedResponse value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }

        public override DetailedResponse ReadJson(JsonReader reader, Type objectType, DetailedResponse existingValue, bool hasExistingValue,
            JsonSerializer serializer)
        {
            var response = new DetailedResponse();
            reader.Read();
            while (reader.TokenType != JsonToken.EndObject)
            {
                switch (reader.Value)
                {
                    case "ok":
                        response.Ok = reader.ReadAsBoolean().GetValueOrDefault(false);
                        break;
                    case "license":
                        response.License = reader.ReadAsString();
                        break;
                    case "data":
                        response.Data = reader.ReadAsString();
                        break;
                    case "status":
                        response.Status = reader.ReadAsString();
                        break;
                    case "station":
                        response.Station = reader.ReadAsDetailedGasStation();
                        break;
                }

                reader.Read();
            }
                    
            return response;
        }
    }

    internal static class JsonReaderTankerkoenigExtension
    {
        public static DetailedGasStation ReadAsDetailedGasStation(this JsonReader reader)
        {
            var station = new DetailedGasStation();

            reader.Read();
            while (reader.TokenType != JsonToken.EndObject)
            {
                if(reader.TokenType == JsonToken.PropertyName)
                    switch (reader.Value)
                    {
                        case "id":
                            station.Id = reader.ReadAsString();
                            break;
                        case "name":
                            station.Name = reader.ReadAsString();
                            break;
                        case "brand":
                            station.Brand = reader.ReadAsString();
                            break;
                        case "street":
                            station.Address.Street = reader.ReadAsString();
                            break;
                        case "houseNumber":
                            station.Address.HouseNumber = reader.ReadAsString();
                            break;
                        case "postCode":
                            station.Address.PostCode = reader.ReadAsInt32().GetValueOrDefault(0);
                            break;
                        case "place":
                            station.Address.Place = reader.ReadAsString();
                            break;
                        case "openingTimes":
                            station.OpeningTimes = reader.ReadAsOpeningTimes();
                            break;
                        case "overrides":
                            station.Overrides = reader.ReadAsStringList();
                            break;
                        case "wholeDay":
                            station.WholeDay = reader.ReadAsBoolean().GetValueOrDefault(false);
                            break;
                        case "isOpen":
                            station.IsOpen = reader.ReadAsBoolean().GetValueOrDefault(false);
                            break;

                        case "e5":
                        {
                            var price = reader.ReadAsDecimal();
                            if (price.HasValue)
                                station.Prices.Add(Fuel.E5, price.Value);

                            break;
                        }
                        case "e10":
                        {
                            var price = reader.ReadAsDecimal();
                            if (price.HasValue)
                                station.Prices.Add(Fuel.E10, price.Value);

                            break;
                        }
                        case "diesel":
                        {
                            var price = reader.ReadAsDecimal();
                            if (price.HasValue)
                                station.Prices.Add(Fuel.Diesel, price.Value);

                            break;
                        }
                        case "lat":
                            station.Coordinates.Latitude = reader.ReadAsDouble().GetValueOrDefault(0.0);
                            break;
                        case "lng":
                            station.Coordinates.Longitude = reader.ReadAsDouble().GetValueOrDefault(0.0);
                            break;
                        case "state":
                            station.State = reader.ReadAsString();
                            break;
                    }
                reader.Read();
            }

            return station;
        }

        public static IDictionary<GasStationId, Result<PriceRequestError, Models.Prices>> ReadAsPrices(
            this JsonReader reader)
        {
            var prices = new Dictionary<GasStationId, Result<PriceRequestError, Models.Prices>>();

            reader.Read(); // {
            reader.Read(); 
            while (reader.TokenType != JsonToken.EndObject)
            {
                if (reader.TokenType == JsonToken.PropertyName)
                {
                    var gasstationId = reader.Value.ToString();
                    var gasstation = reader.ReadAsPrice();
                    prices.Add(gasstationId, gasstation);
                }

                reader.Read();
            }

            return prices;
        }

        public static Result<PriceRequestError, Models.Prices> ReadAsPrice(this JsonReader reader)
        {
            reader.Read();

            if (reader.TokenType != JsonToken.StartObject)
                return null;

            var prices = new Models.Prices();

            reader.Read();


            while (reader.TokenType != JsonToken.EndObject)
            {
                switch (reader.Value)
                {
                    case "status":
                    {
                        var status = reader.ReadAsString();
                        switch (status)
                        {
                            case "no prices":
                                return Result<PriceRequestError, Models.Prices>.Error(PriceRequestError.NoPrices);
                            case "closed":
                                return Result<PriceRequestError, Models.Prices>.Error(PriceRequestError.Closed);
                        }
                        break;
                    }
                    case "e5":
                    {
                        var price = reader.ReadAsDecimal();
                        prices.E5 = price.HasValue
                            ? FSharpOption<decimal>.Some(price.Value)
                            : FSharpOption<decimal>.None;
                        break;
                    }
                    case "e10":
                    {
                        var price = reader.ReadAsDecimal();
                        prices.E10 = price.HasValue
                            ? FSharpOption<decimal>.Some(price.Value)
                            : FSharpOption<decimal>.None;
                        break;
                        }
                    case "diesel":
                    {
                        var price = reader.ReadAsDecimal();
                        prices.Diesel = price.HasValue
                            ? FSharpOption<decimal>.Some(price.Value)
                            : FSharpOption<decimal>.None;
                        break;
                        }

                }

                reader.Read();

            }

            return Result<PriceRequestError, Models.Prices>.Ok(prices);
        }

        public static List<OpeningTime> ReadAsOpeningTimes(this JsonReader reader)
        {
            reader.Read();
            if (reader.TokenType != JsonToken.StartArray)
                throw new JsonException();

            var openingTimes = new List<OpeningTime>();


            reader.Read();
            while (reader.TokenType != JsonToken.EndArray)
            {
                openingTimes.Add(reader.ReadAsOpeningTime());
                reader.Read();
            }

            return openingTimes;
        }

        public static OpeningTime ReadAsOpeningTime(this JsonReader reader)
        {
            if (reader.TokenType != JsonToken.StartObject)
                throw new JsonException();

            var openingTimes = new OpeningTime();

            while (reader.TokenType != JsonToken.EndObject)
            {
                if (reader.TokenType == JsonToken.PropertyName)

                switch (reader.Value)
                {
                    case "text":
                        openingTimes.Text = reader.ReadAsString();
                        break;
                    case "start":
                        openingTimes.Start = reader.ReadAsString();
                        break;
                    case "end":
                        openingTimes.End = reader.ReadAsString();
                        break;
                }

                reader.Read();
            }

            return openingTimes;
        }

        public static List<string> ReadAsStringList(this JsonReader reader)
        {
            reader.Read();

            if (reader.TokenType != JsonToken.StartArray)
                throw new JsonException();

            var list = new List<string>();

            reader.Read();

            while (reader.TokenType == JsonToken.String)
            {
                list.Add(reader.Value.ToString());
                reader.Read();
            }

            return list;
        }
    }
}
