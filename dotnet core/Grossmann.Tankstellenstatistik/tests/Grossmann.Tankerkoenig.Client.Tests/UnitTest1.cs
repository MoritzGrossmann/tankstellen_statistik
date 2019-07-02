using System;
using Grossmann.Tankerapi.Client.Api.Json;
using Grossmann.Tankerapi.Client.Details;
using Newtonsoft.Json;
using Xunit;

namespace Grossmann.Tankerkoenig.Client.Tests
{
    public class UnitTest1
    {
        public class PriceJsonSerializationTests
        {
            private const string DetailAbfrageJson =
                "{" +
                "\"ok\": true," +
                "\"license\": \"CC BY 4.0 -  https://creativecommons.tankerkoenig.de\"," +
                "\"data\": \"MTS-K\"," +
                "\"status\": \"ok\"," +
                "\"station\": {" +
                "\"id\": \"24a381e3-0d72-416d-bfd8-b2f65f6e5802\"," +
                "\"name\": \"Esso Tankstelle\"," +
                "\"brand\": \"ESSO\"," +
                "\"street\": \"HAUPTSTR. 7\"," +
                "\"houseNumber\": \" \"," +
                "\"postCode\": 84152," +
                "\"place\": \"MENGKOFEN\"," +
                "\"openingTimes\": [" +
                "{" +
                "\"text\": \"Mo-Fr\"," +
                "\"start\": \"06:00:00\"," +
                "\"end\": \"22:30:00\"" +
                "}," +
                "{" +
                "\"text\": \"Samstag\"," +
                "\"start\": \"07:00:00\"," +
                "\"end\": \"22:00:00\"" +
                "}," +
                "{" +
                "\"text\": \"Sonntag\"," +
                "\"start\": \"08:00:00\"," +
                "\"end\": \"22:00:00\"" +
                "}" +
                "]," +
                "\"overrides\": [" +
                "\"13.04.2017, 15:00:00 - 13.11.2017, 15:00:00: geschlossen\"" +
                "]," +
                "\"wholeDay\": false," +
                "\"isOpen\": false," +
                "\"e5\": 1.379," +
                "\"e10\": 1.359," +
                "\"diesel\": 1.169," +
                "\"lat\": 48.72210601," +
                "\"lng\": 12.44438439," +
                "\"state\": null" +
                "}" +
                "}\"";

            [Fact]
            public void ReadDecimalValue()
            {
                var json = "2.5";
                decimal value = JsonConvert.DeserializeObject<decimal>(json, new PriceJsonConverter());
                Assert.Equal(2.5m, value);
            }

            [Fact]
            public void ReadCoordinates()
            {
                var val = JsonConvert.DeserializeObject<DetailedResponse>(DetailAbfrageJson,
                    new DetailResponseJsonConverter());
                Assert.IsType<DetailedResponse>(val);
            }
        }
    }

}
