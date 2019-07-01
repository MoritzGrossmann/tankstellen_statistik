using System;
using System.Threading.Tasks;
using Newtonsoft.Json;
using RestSharp;

namespace Grossmann.Tankerapi.Client.Details
{
    internal class DetailsRequest : TankerkoenigRequest
    {
        public GasStationId GasStation { get; set; }

        private const string IdParameter = "id";

        public DetailsRequest(GasStationId gasstation, string serverBaseUrl, string apiKey) : base(serverBaseUrl, apiKey)
        {
            GasStation = gasstation;
        }

        internal override RestRequest GetRequest()
        {
            var request = new RestRequest(Routes.DetailRoute, Method.GET);
            request.AddQueryParameter(IdParameter, GasStation);
            request.AddApiKey(ApiKey);
            return request;
        }

        internal override Type ResponseType { get; }
        internal override async Task<TankerkoenigResponse> Invoke()
        {
            var response = await new RestClient(ServerBaseUrl).ExecuteGetTaskAsync(GetRequest());
            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<DetailedResponse>(response.Content);

            throw new Exception("kam kack zurück"); // TODO: Errorhandling
        }
    }
}
