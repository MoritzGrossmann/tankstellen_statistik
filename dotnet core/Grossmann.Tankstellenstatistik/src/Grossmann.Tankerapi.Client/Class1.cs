using System;
using System.Collections.Generic;
using System.Linq;
using RestSharp;

namespace Grossmann.Tankerapi.Client
{
    public class TankerapiClient
    {
        public string ApiKey { get; set; }
        
        public string TankerApiBaseUrl { get; set; }

        public TankerapiClient(string tankerApiBaseUrl = null, string apiKey = null)
        {
            ApiKey = apiKey;
            TankerApiBaseUrl = tankerApiBaseUrl;
        }   
    }

    public abstract class ITankerapiRequest
    {
        protected string ApiKey { get; set; }

        internal abstract RestRequest GetRequest(string apiKey);

        protected const string ApiKeyParameter = "apikey";
    }

    public class PriceRequest : ITankerapiRequest
    {
        private const string IdsParameter = "ids";
            
        public List<GasStationId> GasStations { get; set; }

        internal override RestRequest GetRequest(string apiKey)
        {
            var request = new RestRequest("prices.php",Method.GET);
            request.AddQueryParameter(IdsParameter, String.Join(",", GasStations));
            request.AddQueryParameter(ApiKeyParameter, apiKey);
            return request;
        }
    }

    public class GasStationId
    {
        private string _value;

        public GasStationId(string value)
        {
            _value = value;
        }

        public static implicit operator GasStationId(string id) => new GasStationId(id);
        public static implicit operator string(GasStationId id) => id._value;
    }

    public class PriceResponse
    {
        private string _status;

        private float e5;

        private float e10;

        private float diesel;
        
        public string Status
        {
            get => _status;
            set => _status = value;
        }

        public float E5
        {
            get => e5;
            set => e5 = value;
        }

        public float E10
        {
            get => e10;
            set => e10 = value;
        }

        public float Diesel
        {
            get => diesel;
            set => diesel = value;
        }
    }
}