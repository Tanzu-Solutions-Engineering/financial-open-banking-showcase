namespace Showcase.SteelToe.Data.Solutions.Domain
{
    public class Account
    {
        public string Id { get; set; }

        public string? Data { get; set; } 

        public override string ToString()
        {
            return $"Account: Id: {Id}, Data: {Data}";
        }
    }

}
