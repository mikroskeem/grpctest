/*
 * WTFPL LICENSE
 */

package eu.mikroskeem.grpctest

import eu.mikroskeem.grpctest.rpc.PlayersImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Mark Vainomaa
 */
class Main: JavaPlugin() {
    private lateinit var grpcServer: Server
    private lateinit var executorService: ExecutorService

    //private val host = "0.0.0.0"
    private val port = 50005

    override fun onLoad() {
        executorService = Executors.newWorkStealingPool()
        grpcServer = ServerBuilder
                .forPort(port)
                .addService(PlayersImpl(server))
                .executor(executorService)
                .build()
    }

    override fun onEnable() {
        grpcServer.start()
    }

    override fun onDisable() {
        grpcServer.shutdown()
        executorService.shutdown()
        try {
            executorService.awaitTermination(2000, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            executorService.shutdownNow()
        }
    }
}