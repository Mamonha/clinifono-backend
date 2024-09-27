package com.app.clinifono.dto.consulta;

import com.app.clinifono.entities.Status;
import jakarta.validation.constraints.NotNull;

public record ConsultaConfirmDto(@NotNull Status status) {
}
