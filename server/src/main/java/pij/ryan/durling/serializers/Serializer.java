package pij.ryan.durling.serializers;

abstract class Serializer {

    public Serializer() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::serialize));
    }

    /**
     * serialize data
     */
    abstract void serialize();
}
