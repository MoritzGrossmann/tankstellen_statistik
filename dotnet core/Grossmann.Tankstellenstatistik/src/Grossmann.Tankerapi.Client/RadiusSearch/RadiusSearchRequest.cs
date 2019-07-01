using System;
using System.Collections.Generic;
using System.Globalization;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using Grossmann.Tankerapi.Client.Models;
using RestSharp;

namespace Grossmann.Tankerapi.Client.RadiusSearch
{
    internal class RadiusSearchRequest : TankerkoenigRequest
    {
        private const string LatitudeParameter = "lat";

        private const string LongitudeParameter = "lng";

        private const string RadiusParameter = "rad";

        private const string SortParameter = "sort";

        private const string TypeParameter = "type";

        public RadiusSearchRequest(Coordinates coordinates, decimal radius, FuelType fuelType, Sort sorting, string serverBaseUrl, string apiKey) : base(serverBaseUrl, apiKey)
        {
            Coordinates = coordinates;
            Radius = radius;
            FuelType = fuelType;
            Sorting = sorting;
        }

        public Coordinates Coordinates { get; set; }

        public decimal Radius { get; set; }

        public FuelType FuelType { get; set; }

        public Sort Sorting { get; }

        public Sort Sort { get; set; }


        internal override RestRequest GetRequest()
        {
            var request = new RestRequest(Routes.RadiusRoute, Method.GET);
            request.AddQueryParameter(LatitudeParameter, Coordinates.Latitude.ToString(CultureInfo.InvariantCulture));
            request.AddQueryParameter(LongitudeParameter, Coordinates.Longitude.ToString(CultureInfo.InvariantCulture));
            request.AddQueryParameter(RadiusParameter, Radius.ToString(CultureInfo.InvariantCulture));
            request.AddQueryParameter(SortParameter, Sort.QueryString());
            request.AddQueryParameter(TypeParameter, FuelType.QueryString());
            request.AddApiKey(ApiKey);
            return request;
        }

        internal override async Task<TankerkoenigResponse> Invoke()
        {
            var client = new RestClient(ServerBaseUrl);
            var response = await client.ExecuteGetTaskAsync<RadiusSearchResponse>(GetRequest());

            if(response.IsSuccessful)
                return response.Data;

            throw new Exception("kam kack zurück"); // TODO: Errorhandling
        }

        internal override Type ResponseType => typeof(RadiusSearchResponse);

    }

    public enum Sort
    {
        Price,
        Distance
    }

    public static class SortExtension
    {
        public static string QueryString(this Sort sort)
        {
            switch (sort)
            {
                case Sort.Price:
                    return "price";
                case Sort.Distance:
                    return "break";
                default:
                    throw new ArgumentOutOfRangeException(nameof(sort), sort, null);
            }
        }
    }

    public static class FuelTypeExtension
    {
        public static string QueryString(this FuelType fuelType)
        {
            return fuelType.ToString().ToLower();
        }
    }

    public enum FuelType
    {
        E5 = 0, E10 = 1, Diesel = 2, All = 3
    }
}
