using System;
using System.Collections.Generic;
using System.Text;
using Grossmann.Tankerapi.Client.Models;

namespace Grossmann.Tankerapi.Client.RadiusSearch
{
    public class RadiusSearchResponse : TankerkoenigResponse
    {
        public List<GasStationWithDistance> Stations { get; set; }
    }

    public class GasStationWithDistance : GasStation
    {
        public double Dist { get; set; }
    }
}
