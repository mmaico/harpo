package harpo.network.p2p.domain.model

import harpo.network.p2p.domain.model.node.External
import harpo.network.p2p.domain.model.node.Node


data class Closest(val nodes: List<Node>, val closestFrom: External)


