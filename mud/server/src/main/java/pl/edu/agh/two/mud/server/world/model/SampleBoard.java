package pl.edu.agh.two.mud.server.world.model;

public class SampleBoard extends Board {
    public SampleBoard() {
        Field[][] fields = new Field[1][3];

        fields[0][0] = new Field(0, 0, "Pole startowe", "Budzisz sie, a za plecami masz portal");
        fields[0][1] = new Field(0, 1, "Sciezka na wschod", "Znajdujesz sie na sciezce biegnacej na wschod");
        fields[0][2] = new Field(0, 2, "Koniec swiata", "Znajdujesz sie na wschodnim koncu swiata");

        fields[0][0].setBoard(this);
        fields[0][1].setBoard(this);
        fields[0][2].setBoard(this);

        this.setFields(fields);
        this.setStartingField(fields[0][0]);
    }
}
