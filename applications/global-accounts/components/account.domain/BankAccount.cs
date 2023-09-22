using System.Collections;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Showcase.SteelToe.Data.Solutions.Domain
{
    public class BankAccount
    {
        public string? id { get; set; } = "";
        public string bank_id{ get; set; } = "";
        public string label { get; set; } = "";
        public string? number { get; set; } = "";
        public string? product_code { get; set; } = "";
        public Balance? balance { get; set; } =  new Balance();
        public AccountRouting[]? account_routings { get; set; } = null;
        public string[]? views_basic { get; set; }  = null;
        public string? key { get; set; }  ="";
        
    }
}