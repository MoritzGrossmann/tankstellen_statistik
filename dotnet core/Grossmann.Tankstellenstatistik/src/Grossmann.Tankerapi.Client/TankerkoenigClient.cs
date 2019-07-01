using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Grossmann.Tankerapi.Client.Core;
using Grossmann.Tankerapi.Client.Details;
using Grossmann.Tankerapi.Client.Models;
using Grossmann.Tankerapi.Client.Prices;
using Grossmann.Tankerapi.Client.RadiusSearch;

namespace Grossmann.Tankerapi.Client
{
    public class TankerkoenigClient : ITankerkoenigClient
    {
        public string ApiKey { get; set; }
        
        public string TankerkoenigBaseUrl { get; set; }

        private const string TestApiKey = "00000000-0000-0000-0000-000000000002";

        public TankerkoenigClient(string apiKey = null, string tankerkoenigBaseUrl = null)
        {
            TankerkoenigBaseUrl = tankerkoenigBaseUrl ?? Routes.ServerUrl;
            ApiKey = apiKey ?? TestApiKey;
        }

        public async Task<IDictionary<GasStationId, Result<PriceRequestError, Models.Prices>>> GetPricesAsync(IEnumerable<GasStationId> gasstations)
        {
            var response = await new PriceRequest(gasstations.ToList(), TankerkoenigBaseUrl,ApiKey).Invoke();
            return (response as PriceResponse)?.Prices;
        }

        public async Task<Result<PriceRequestError, Models.Prices>> GetPriceAsync(GasStationId gasStationId)
        {
            var response = await new PriceRequest(new List<GasStationId>() {gasStationId}, TankerkoenigBaseUrl, ApiKey).Invoke();
            var hasPrices = (response as PriceResponse).Prices.TryGetValue(gasStationId, out var prices);
            if (hasPrices)
                return prices;

            return null;
        }

        public async Task<RadiusSearchResponse> RadiusSearchAsync(Coordinates coordinates, decimal radius, FuelType type = FuelType.All, Sort sort = Sort.Price)
        {
            var response = await new RadiusSearchRequest(coordinates, radius, type, sort, TankerkoenigBaseUrl, ApiKey).Invoke();
            return (RadiusSearchResponse) response;
        }

        public async Task<DetailedResponse> GetDetailsAsync(GasStationId gasStationId)
        {
            var response = await new DetailsRequest(gasStationId, TankerkoenigBaseUrl, ApiKey).Invoke();
            return response as DetailedResponse;
        }
    }

    public interface ITankerkoenigClient
    {
        Task<IDictionary<GasStationId, Result<PriceRequestError, Models.Prices>>> GetPricesAsync(IEnumerable<GasStationId> gasstations);

        Task<Result<PriceRequestError, Models.Prices>> GetPriceAsync(GasStationId gasStationId);

        Task<RadiusSearchResponse> RadiusSearchAsync(Coordinates coordinates, decimal radius, FuelType type = FuelType.All, Sort sort = Sort.Price);

        Task<DetailedResponse> GetDetailsAsync(GasStationId gasStationId);
    }
}