using System;
using System.Collections.Generic;
using Grossmann.Tankerapi.Client.Core;
using Grossmann.Tankerapi.Client.Prices;

namespace Grossmann.Tankerapi.Client.Api.Responses
{
    internal class BarePriceResponse : TankerkoenigResponse
    {
        public IDictionary<string, BarePrices> Prices { get; set; }

        public PriceResponse ToPriceResponse()
        {
            var response = new PriceResponse();
            response.Ok = Ok;
            response.Message = Message;
            response.License = License;
            response.Data = Data;
            response.Status = Status;

            response.Prices = new Dictionary<GasStationId, Result<PriceRequestError, Models.Prices>>();

            foreach (var barePrices in Prices)
            {
                switch (barePrices.Value.Status)
                {
                    case "open":
                        response.Prices.Add(barePrices.Key, Result<PriceRequestError, Models.Prices>.Ok(barePrices.Value.ToPrices()));
                        break;
                    case "no prices":
                        response.Prices.Add(barePrices.Key, Result<PriceRequestError, Models.Prices>.Error(PriceRequestError.NoPrices));
                        break;
                    case "closed":
                        response.Prices.Add(barePrices.Key, Result<PriceRequestError, Models.Prices>.Error(PriceRequestError.Closed));
                        break;
                    default:
                        throw new Exception(); // TODO: Fehlerbehandlung
                }
            }

            return response;
        }
    }
}