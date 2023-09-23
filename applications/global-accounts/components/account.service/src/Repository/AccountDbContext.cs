using Imani.Solutions.Core.API.Util;
using Showcase.SteelToe.Data.Solutions.Domain;
using Microsoft.EntityFrameworkCore;
using System.Reflection;
using Microsoft.EntityFrameworkCore.Infrastructure;
using System.IO;
using System;

namespace Showcase.SteelToe.Data.Repository
{
    public class AccountDbContext : DbContext
    {
        private ISettings settings = new ConfigSettings();
        private string connectionString;
        private const string TESTING_EXE_NAME = "testhost.dll";
        private const string defaultSchemaName = "Account";

        private readonly string schemaName;

        public AccountDbContext(DbContextOptions options) : base(options)
        {
            this.schemaName = settings.GetProperty("SCHEMA_NAME",defaultSchemaName);
            this.connectionString = settings.GetProperty("ConnectionString","");
        }


        protected override void OnConfiguring(DbContextOptionsBuilder options){
            
            var exeName = Path.GetFileName(Assembly.GetEntryAssembly().Location);

            if(TESTING_EXE_NAME.Equals(exeName))
            {
                //let Account.Data be null
                   options
                    .UseInMemoryDatabase("UserContextWithNullCheckingDisabled", 
                    b => b.EnableNullChecks(false));

                return; //do not Postgres connections
            }

            this.connectionString = settings.GetProperty("ConnectionString");

            options.UseNpgsql(connectionString,
            x => x.MigrationsHistoryTable("__EFMigrationsHistory", schemaName));
        }
            

        protected override void OnModelCreating(ModelBuilder builder){

            builder.HasDefaultSchema(schemaName);    //To all the tables.

            builder.Entity<Account>()
            .HasKey(b => b.Id).HasName("data_id"); //primary key

            builder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
        }

        public DbSet<Account> Account { get; set; }

        public static void Migrate(DatabaseFacade database)
        {
             database.Migrate();
        }
    }

}
