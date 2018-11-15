package com.github.atais.entity

import java.util
import java.util.Collections

import javax.persistence._
import org.eclipse.persistence.annotations.PrivateOwned

@Entity
class EntityB {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Int = _

  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "entityB", fetch = FetchType.LAZY)
  @PrivateOwned
  var entitiesA: util.List[EntityA] = Collections.emptyList[EntityA]()

}
