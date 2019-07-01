using System;
using System.Threading.Tasks;

namespace Grossmann.Tankerapi.Client.Console
{
    class Program
    {
        static async Task Main(string[] args)
        {
            ITankerkoenigClient client = new TankerkoenigClient();

            var resp = await client.GetPriceAsync("24a381e3-0d72-416d-bfd8-b2f65f6e5802");
            System.Console.WriteLine(resp);
        }
    }
}
