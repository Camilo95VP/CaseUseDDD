package co.com.sofkau.entrenamento.curso;


import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofkau.entrenamiento.curso.Mentoria;
import co.com.sofkau.entrenamiento.curso.commands.AgregarDirectrizMentoria;
import co.com.sofkau.entrenamiento.curso.events.CursoCreado;
import co.com.sofkau.entrenamiento.curso.events.DirectrizAgregadaAMentoria;
import co.com.sofkau.entrenamiento.curso.events.MentoriaCreada;
import co.com.sofkau.entrenamiento.curso.values.CursoId;
import co.com.sofkau.entrenamiento.curso.values.Descripcion;
import co.com.sofkau.entrenamiento.curso.values.Directiz;
import co.com.sofkau.entrenamiento.curso.values.Fecha;
import co.com.sofkau.entrenamiento.curso.values.MentoriaId;
import co.com.sofkau.entrenamiento.curso.values.Nombre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AgregarDirectrizMentoriaUseCaseTest {

    @InjectMocks
    private AgregarDirectrizMentoriaUseCase useCase;
    @Mock
    private DomainEventRepository repository;

    @Test
    void agregarDirectrizAMentoria() {

        //arrange
        CursoId cursoId = CursoId.of("Domain Driven Desing");
        MentoriaId mentoriaId = MentoriaId.of("123456");
        Directiz directiz = new Directiz("SofkaU");
        var command = new AgregarDirectrizMentoria(cursoId,mentoriaId,directiz);

        when(repository.getEventsBy("Domain Driven Desing")).thenReturn(history());
        useCase.addRepository(repository);

        //act
        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(command.getMentoriaId().value())
                .syncExecutor(useCase, new RequestCommand<>(command))
                .orElseThrow()
                .getDomainEvents();

        //assert
        var event = (DirectrizAgregadaAMentoria)events.get(0);
        Assertions.assertEquals("SofkaU", event.getDirectiz().value());

    }
    private List<DomainEvent> history() {

        var nombreCurso = new Nombre("Aplicacion Empresarial");
        var descripcionCurso = new Descripcion("Training SofkaU");
        var cursoid = CursoId.of("Domain Driven Desing");
        var eventoCurso = new CursoCreado(nombreCurso,descripcionCurso);

        var nombreMentoria = new Nombre("Programacion funcional/Reactiva");
        var fecha = new Fecha(LocalDateTime.now(), LocalDate.now());
        var mentoriaId = MentoriaId.of("123456");
        var eventoMentoria = new MentoriaCreada(mentoriaId,nombreMentoria,fecha);

        eventoCurso.setAggregateRootId(cursoid.value());
        return List.of(eventoCurso,eventoMentoria);
    }
}

