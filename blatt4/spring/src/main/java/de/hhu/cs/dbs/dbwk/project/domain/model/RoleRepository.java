package de.hhu.cs.dbs.dbwk.project.domain.model;

import java.util.Set;

public interface RoleRepository {

    /**
     * Gibt alle möglichen Nutzertypen als Menge von {@link Role}-Instanzen zurück, indem die
     * Datenbank angefragt wird. also nutzer buerger und gaertner
     * (schöner wäre es wenn man es nicht hardcodet sondern dynamisch, sodass jede Rolle in Zukunft möglich ist)
     *
     *
     * @return {@link Set<Role>}, die {@link Role}-Instanzen zurückgibt.
     */
    Set<Role> findAllRoles();
}
