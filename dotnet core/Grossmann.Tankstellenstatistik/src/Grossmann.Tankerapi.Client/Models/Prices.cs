using Microsoft.FSharp.Core;

namespace Grossmann.Tankerapi.Client.Models
{
    public class Prices
    {
        private FSharpOption<decimal> _e5;

        private FSharpOption<decimal> _e10;

        private FSharpOption<decimal> _diesel;
        
        public FSharpOption<decimal> E5
        {
            get => _e5;
            set => _e5 = value;
        }

        public FSharpOption<decimal> E10
        {
            get => _e10;
            set => _e10 = value;
        }

        public FSharpOption<decimal> Diesel
        {
            get => _diesel;
            set => _diesel = value;
        }
    }
}