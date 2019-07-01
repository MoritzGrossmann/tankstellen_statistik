namespace Grossmann.Tankerapi.Client
{
    public class GasStationId
    {
        internal string Value;

        public GasStationId(string value)
        {
            Value = value;
        }

        public static implicit operator GasStationId(string id) => new GasStationId(id);
        public static implicit operator string(GasStationId id) => id.Value;
    }
}