/*
 * WTFPL LICENSE
 */

package eu.mikroskeem.grpctest.rpc

import com.google.protobuf.Empty
import eu.mikroskeem.grpctest.proto.PlayerList
import eu.mikroskeem.grpctest.proto.PlayersGrpc
import io.grpc.stub.StreamObserver
import org.bukkit.Server
import org.bukkit.entity.Player

/**
 * @author Mark Vainomaa
 */
class PlayersImpl(private val server: Server): PlayersGrpc.PlayersImplBase() {
    override fun listPlayers(request: Empty, responseObserver: StreamObserver<PlayerList>) {
        val reply = PlayerList.newBuilder()
                .addAllPlayers(server.onlinePlayers.map(Player::getName))
                .build()
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}