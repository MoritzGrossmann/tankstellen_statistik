using System;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using RestSharp;

namespace Grossmann.Tankerapi.Client
{
    internal abstract class TankerkoenigRequest
    {
        protected string ApiKey { get; set; }

        protected string ServerBaseUrl { get; set; }

        public TankerkoenigRequest(string serverBaseUrl, string apiKey)
        {
            ServerBaseUrl = serverBaseUrl;
            ApiKey = apiKey;
        }

        internal abstract RestRequest GetRequest();

        internal abstract Type ResponseType { get; }

        internal abstract Task<TankerkoenigResponse> Invoke();

        internal const string ApiKeyParameter = "apikey";
    }

    internal static class TankerkoenigRequestExtension
    {
        internal static void AddApiKey(this RestRequest request, string apiKey)
        {
            request.AddQueryParameter(TankerkoenigRequest.ApiKeyParameter, apiKey);
        }
    }
}