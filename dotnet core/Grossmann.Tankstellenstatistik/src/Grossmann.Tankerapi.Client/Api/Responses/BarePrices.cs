using Microsoft.FSharp.Core;

namespace Grossmann.Tankerapi.Client.Api.Responses
{
    internal class BarePrices
    {
        public string Status { get; set; }

        public string E5 { get; set; }

        public string E10 { get; set; }

        public string Diesel { get; set; }

        internal Models.Prices ToPrices()
        {
            var prices = new Models.Prices();

            var hasE5 = decimal.TryParse(E5, out var e5Res);
            prices.E5 = hasE5 ? FSharpOption<decimal>.Some(e5Res) : FSharpOption<decimal>.None;

            var hasE10 = decimal.TryParse(E10, out var e10Res);
            prices.E10 = hasE10 ? FSharpOption<decimal>.Some(e10Res) : FSharpOption<decimal>.None;

            var hasDiesel = decimal.TryParse(Diesel, out var dieselRes);
            prices.Diesel = hasDiesel ? FSharpOption<decimal>.Some(dieselRes) : FSharpOption<decimal>.None;

            return prices;
        }
    }
}