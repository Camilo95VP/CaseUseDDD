package co.com.sofkau.entrenamento.curso;


import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofkau.entrenamiento.curso.commands.AgregarDirectrizMentoria;
import co.com.sofkau.entrenamiento.curso.values.CursoId;
import co.com.sofkau.entrenamiento.curso.values.Directiz;
import co.com.sofkau.entrenamiento.curso.values.MentoriaId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AgregarDirectrizMentoriaUseCaseTest {

    @InjectMocks
    private AgregarDirectrizMentoriaUseCase useCase;
    @Mock
    private DomainEventRepository repository;

    @Test
    void agregarDirectrizAMentoria() {
        //arrange
        CursoId cursoId = CursoId.of("curso");
        MentoriaId mentoriaId = MentoriaId.of("ment");
        Directiz directiz = new Directiz("xdxdxd");
        var command = new AgregarDirectrizMentoria(cursoId,mentoriaId,directiz);

    }
}

