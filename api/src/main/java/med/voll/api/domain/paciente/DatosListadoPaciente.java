package med.voll.api.domain.paciente;

public record DatosListadoPaciente(String nombre, String documento, String email) {

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getDocumento(), paciente.getEmail());
    }
}