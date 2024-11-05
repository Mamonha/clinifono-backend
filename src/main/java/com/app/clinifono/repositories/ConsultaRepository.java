package com.app.clinifono.repositories;

import com.app.clinifono.entities.Consulta;
import com.app.clinifono.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    long countByStatus(Status status);

    @Query("SELECT new map(p.nome as paciente, COUNT(c) as totalConsultas) " +
            "FROM Consulta c JOIN c.paciente p GROUP BY p.nome")
    List<Map<String, Object>> findConsultasPorPaciente();

    @Query("SELECT new map(MONTH(c.dataAgendamento) as mes, COUNT(c) as totalConsultas) " +
            "FROM Consulta c GROUP BY MONTH(c.dataAgendamento)")
    List<Map<String, Object>> findConsultasPorMes();

    int countByDataConsultaBetween(LocalDate inicio, LocalDate fim);
}