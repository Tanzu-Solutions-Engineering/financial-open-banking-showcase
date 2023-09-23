using System;
using Newtonsoft.Json.Linq;
using Showcase.SteelToe.Data.Solutions.Domain;

namespace Showcase.SteelToe.Data.Solutions.Consumer.Mapping
{
    public class AccountMapper : IAccountMapper
    {
      /// <summary>
      /// Convert Json to account
      /// </summary>
      /// <param name="json">the raw data</param>
      /// <returns>the account</returns>
          public Account ToAccount(string json)
          {
            var jacctObj = JObject.Parse(json);

            JToken id = jacctObj["id"];
            if(id == null)
              throw new ArgumentException($"Id is missing from json {json}");

            JToken bank_id = jacctObj["bank_id"];
            if(bank_id == null)
              throw new ArgumentException($"bank_id is missing from json {json}");

            var account = new Account();
            account.Id = $"{bank_id.ToString()}|{id.ToString()}";
            account.Data = json;

            return account;

          }
    }
}