namespace Grossmann.Tankerapi.Client.Api.Responses
{
    internal class BareGasStaion
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Brand { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public string E5 { get; set; }
        public string E10 { get; set; }
        public string Diesel { get; set; }
        public string Price { get; set; }
        public string Street { get; set; }
        public string Place { get; set; }
        public string HouseNumber { get; set; }
        public string PostCode { get; set; }
        public bool IsOpen { get; set; }
    }
}