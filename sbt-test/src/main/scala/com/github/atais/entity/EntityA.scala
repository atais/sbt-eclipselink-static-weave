package com.github.atais.entity

import javax.persistence._

@Entity
class EntityA {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Int = _

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entityB_id")
  var entityB: EntityB = _

}
