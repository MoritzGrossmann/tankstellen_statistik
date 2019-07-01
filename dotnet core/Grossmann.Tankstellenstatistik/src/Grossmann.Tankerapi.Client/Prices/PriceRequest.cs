using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Grossmann.Tankerapi.Client.Api.Responses;
using Newtonsoft.Json;
using RestSharp;

namespace Grossmann.Tankerapi.Client.Prices
{
    internal class PriceRequest : TankerkoenigRequest
    {
        private const string IdsParameter = "ids";

        public PriceRequest( List<GasStationId> gasStations, string serverBaseUrl, string apiKey) : base(serverBaseUrl, apiKey)
        {
            GasStations = gasStations;
        }

        public List<GasStationId> GasStations { get; set; }

        internal override RestRequest GetRequest()
        {
            var request = new RestRequest(Routes.PriceRoute, Method.GET);
            request.AddQueryParameter(IdsParameter, String.Join(",", GasStations.Select(g => g.Value)));
            request.AddQueryParameter(ApiKeyParameter, ApiKey);
            return request;
        }

        internal override Type ResponseType { get; }

        internal override async Task<TankerkoenigResponse> Invoke()
        {
            var response = await new RestClient(ServerBaseUrl).ExecuteGetTaskAsync(GetRequest());
            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<BarePriceResponse>(response.Content).ToPriceResponse();

            throw new Exception("kam kack zurück"); // TODO: Errorhandling
        }
    }
}