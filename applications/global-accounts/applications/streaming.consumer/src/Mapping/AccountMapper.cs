using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Showcase.SteelToe.Data.Solutions.Domain;

namespace Showcase.SteelToe.Data.Solutions.Consumer.Mapping
{
    public class AccountMapper : IAccountMapper
    {

          public Account ToAccount(string json){
            return new Account();
          }
    }
}