package backend.academy.picture;

public class EasyHangmanPicture extends HangmanPicture {
    public EasyHangmanPicture() {
        pictures.add("""
              _____
              |   |
              |   O
              |  /|\\
              | _/ \\
            __|__
            """);
        pictures.add("""
              _____
              |   |
              |   O
              |  /|\\
              | _/ \\_
            __|__
            """);
    }
}
