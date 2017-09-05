/*
 * WTFPL LICENSE
 */

package eu.mikroskeem.grpctest

import com.google.protobuf.Empty
import eu.mikroskeem.grpctest.proto.PlayersGrpc
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

/**
 * @author Mark Vainomaa
 */
object App {
    @JvmStatic
    fun main(vararg args: String) {
        run {
            val host = args.getOrNull(0) ?: return@run
            val port = Integer.parseInt(args.getOrNull(1) ?: return@run)

            client(host, port)

            return
        }
        println("Usage: app <host> <port>")
        exitProcess(1)
    }
}

fun App.client(host: String, port: Int) {
    val channel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext(true)
            .build()
    val client = PlayersGrpc.newBlockingStub(channel)

    // Do request and dump players
    val playersList = client.listPlayers(Empty.getDefaultInstance()).playersList.toList()
    println("Online players: $playersList")

    channel.shutdown().awaitTermination(2, TimeUnit.SECONDS)
}