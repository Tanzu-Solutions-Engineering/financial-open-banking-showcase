using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Steeltoe.Stream.Attributes;
using Steeltoe.Stream.Messaging;

namespace Showcase.SteelToe.Data.Solutions.Consumer.Consumers
{
    [EnableBinding(typeof(ISink))]
    public class AccountConsumer
    {

         private readonly ILogger<AccountConsumer> log;

        public delegate IAccountRepository CreateRepository();

        private CreateRepository repositoryCreator;

        public AccountConsumer(IServiceProvider serviceProvider,ILogger<AccountConsumer> logger)
        : this((delegate {
            return serviceProvider.GetRequiredService<IAccountRepository>();
        }),logger)
        {}

        public AccountConsumer(CreateRepository repositoryCreator,ILogger<AccountConsumer> log)
        {
            this.repositoryCreator = repositoryCreator;
            this.log = log;
        }

        [StreamListener(ISink.INPUT)]
        public void Accept(Account account)
        {
            log.LogInformation($"ACCOUNT: ${account}");

            var repository = repositoryCreator();

            repository.Save(account);
        }
    }
}