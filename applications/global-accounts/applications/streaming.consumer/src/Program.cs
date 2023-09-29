using System.Security.AccessControl;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Showcase.SteelToe.Data.Repository;
using Showcase.SteelToe.Data.Solutions.Consumer.Consumers;
using Steeltoe.Common.Hosting;
using Steeltoe.Extensions.Configuration.Placeholder;
using Steeltoe.Extensions.Logging;
using Steeltoe.Stream.Extensions;
using System;

namespace Showcase.SteelToe.Data.Solutions.Consumer
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = CreateHostBuilder(args).Build();

           Console.WriteLine("Runnning migration");

            using (var scope = host.Services.CreateScope())
            {
                var db = scope.ServiceProvider.GetRequiredService<AccountDbContext>();
                
                 AccountDbContext.Migrate(db.Database);
                 Console.WriteLine("Migrated data");
            }

            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .UseCloudHosting(58628)
                .UseDefaultServiceProvider(options =>
                    options.ValidateScopes = false)
                .ConfigureLogging((builderContext, loggingBuilder) =>
                {
                    // Add Steeltoe Dynamic Logging provider
                    loggingBuilder.AddDynamicConsole();
                })
                
                .ConfigureLogging((context, builder) => builder.AddDynamicConsole())
                .AddStreamServices<AccountConsumer>()
                .ConfigureWebHostDefaults(webBuilder => 
                { 
                    webBuilder.UseStartup<Startup>(); 
                    webBuilder.AddPlaceholderResolver();
                });
    }
}
