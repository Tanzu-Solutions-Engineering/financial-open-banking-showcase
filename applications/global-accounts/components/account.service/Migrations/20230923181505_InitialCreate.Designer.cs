﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;
using Showcase.SteelToe.Data.Repository;

#nullable disable

namespace account.service.Migrations
{
    [DbContext(typeof(AccountDbContext))]
    [Migration("20230923181505_InitialCreate")]
    partial class InitialCreate
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasDefaultSchema("Sample")
                .HasAnnotation("ProductVersion", "7.0.10")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("Showcase.SteelToe.Data.Solutions.Domain.Account", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("text");

                    b.Property<string>("Data")
                        .HasColumnType("text");

                    b.HasKey("Id")
                        .HasName("data_id");

                    b.ToTable("Account", "Sample");
                });
#pragma warning restore 612, 618
        }
    }
}