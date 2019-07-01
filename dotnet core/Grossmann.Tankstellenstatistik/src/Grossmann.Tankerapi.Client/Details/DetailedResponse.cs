using System.Collections.Generic;

namespace Grossmann.Tankerapi.Client.Details
{
    public class OpeningTime
    {
        public string Text { get; set; }
        public string Start { get; set; }
        public string End { get; set; }
    }

    public class DetailedGasStation : Models.GasStation
    {
        public List<OpeningTime> OpeningTimes { get; set; }
        public List<string> Overrides { get; set; }
        public bool WholeDay { get; set; }
        public object State { get; set; }
    }

    public class DetailedResponse : TankerkoenigResponse
    {
        public DetailedGasStation Station { get; set; }
    }
}
