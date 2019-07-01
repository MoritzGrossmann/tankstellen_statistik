using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.FSharp.Core;

namespace Grossmann.Tankerapi.Client.Models
{
    public class GasStation
    {
        public GasStationId Id { get; set; }
        public string Name { get; set; }
        public string Brand { get; set; }
        public Coordinates Coordinates { get; set; }
        public IDictionary<Fuel, decimal> Prices { get; set; }
        public FSharpOption<decimal> Price { get; set; }
        public bool IsOpen { get; set; }

    }
}
