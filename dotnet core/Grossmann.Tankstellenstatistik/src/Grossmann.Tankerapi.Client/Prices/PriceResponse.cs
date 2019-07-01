using System.Collections.Generic;
using Grossmann.Tankerapi.Client.Core;

namespace Grossmann.Tankerapi.Client.Prices
{
    public class PriceResponse : TankerkoenigResponse
    {
        public IDictionary<GasStationId, Result<PriceRequestError, Models.Prices>> Prices { get; set; }
    }
}