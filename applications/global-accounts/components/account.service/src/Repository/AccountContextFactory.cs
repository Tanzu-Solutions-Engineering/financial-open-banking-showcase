using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;

namespace Showcase.SteelToe.Data.Repository
{
    public class AccountContextFactory : IDesignTimeDbContextFactory<AccountDbContext>
    {
        public AccountDbContext CreateDbContext(string[] args)
        {
             var optionsBuilder = new DbContextOptionsBuilder<AccountDbContext>();

            return new AccountDbContext(optionsBuilder.Options);
        }
    }
}