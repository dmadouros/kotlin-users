package me.dmadouros.user.application.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Scope
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.visitor.ChangeExecListener
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.command.core.helpers.ChangeExecListenerCommandStep
import liquibase.command.core.helpers.DatabaseChangelogCommandStep
import liquibase.command.core.helpers.DbUrlConnectionCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database
import java.io.OutputStream
import java.sql.DriverManager
import java.util.logging.Logger

fun configureDatabase(jdbcUrl: String) {
    migrate(jdbcUrl)
    Database.connect(createDataSource(jdbcUrl))
}

private fun createDataSource(jdbcUrl: String): HikariDataSource {
    val logger = Logger.getLogger("Application")
    logger.info("Creating DataSource for: $jdbcUrl...")
    val dataSource =
        HikariDataSource(
            HikariConfig().apply {
                driverClassName = "org.postgresql.Driver"
                this.jdbcUrl = jdbcUrl
                maximumPoolSize = 3
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            },
        )
    logger.info("Datasource Created")
    return dataSource
}

private fun migrate(jdbcUrl: String) {
    val logger = Logger.getLogger("Application")
    logger.info("Migrating Database...")

    val connection = DriverManager.getConnection(jdbcUrl)
    connection.use {
        val database =
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
        val scopeObjects =
            mapOf(
                Scope.Attr.database.name to database,
                Scope.Attr.resourceAccessor.name to ClassLoaderResourceAccessor(),
            )

        Scope.child(scopeObjects) {
            val updateCommand =
                CommandScope(*UpdateCommandStep.COMMAND_NAME).apply {
                    addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database)
                    addArgumentValue(
                        UpdateCommandStep.CHANGELOG_FILE_ARG,
                        "db/changelog/db.changelog-master.yml",
                    )
                    addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, Contexts().toString())
                    addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, LabelExpression().originalString)
                    addArgumentValue(
                        ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG,
                        null as ChangeExecListener?,
                    )
                    addArgumentValue(
                        DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS,
                        ChangeLogParameters(database),
                    )

                    setOutput(
                        OutputStream
                            .nullOutputStream(),
                    ) // Suppress output to stdout (logging will still occur)
                }
            updateCommand.execute()
        }
    }
    logger.info("Database Migrated")
}

// suspend fun <T> dbQuery(block: () -> T): T =
//    withContext(Dispatchers.IO) {
//        transaction {
//            //        addLogger(StdOutSqlLogger)
//            block()
//        }
//    }
