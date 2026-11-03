import Models.Pokedex;
import Models.Adestrador;
import Services.PokedexServices;
import Services.AdestradorServices;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Instanciamos los dos servicios de Hibernate para interactuar con PostgreSQL con CRUD
        PokedexServices servicioPokedex = new PokedexServices();
        AdestradorServices servicioAdestrador = new AdestradorServices();

        System.out.println("---INSERIR 10 POKEMONS NA TABLA POKEDEX---");

        servicioPokedex.crearPokemon(new Pokedex("Pikachu", new BigDecimal("6.0"), "Rato eléctrico"));
        servicioPokedex.crearPokemon(new Pokedex("Charmander",  new BigDecimal("16.0"), "Lagarto de fogo"));
        servicioPokedex.crearPokemon(new Pokedex("Squirtle",  new BigDecimal("26.0"), "Tartaruga de auga"));
        servicioPokedex.crearPokemon(new Pokedex("Bulbasaur",  new BigDecimal("36.0"), "Semente de planta"));
        servicioPokedex.crearPokemon(new Pokedex("Eevee",  new BigDecimal("6.2"), "Evolución variada"));
        servicioPokedex.crearPokemon(new Pokedex("Snorlax",  new BigDecimal("11.0"), "Dormilón enorme"));
        servicioPokedex.crearPokemon(new Pokedex("Gengar",  new BigDecimal("6.9"), "Sombra pantasma"));
        servicioPokedex.crearPokemon(new Pokedex("Mewtwo",  new BigDecimal("16.3"), "Psíquico lendario"));
        servicioPokedex.crearPokemon(new Pokedex("Jigglypuff", new BigDecimal("6.1"), "Globo cantante"));
        servicioPokedex.crearPokemon(new Pokedex("Meowth",  new BigDecimal("20.0"), "Gato con moeda"));

        System.out.println("---INSERIR 2 ADESTRADORES NA TÁBOA ADESTRADOR---");

        servicioAdestrador.crearAdestrador(new Adestrador("Ash Ketchum", new Date())); // Guarda el entrenador 1 en la BD
        servicioAdestrador.crearAdestrador(new Adestrador("Misty Waterflower", new Date())); // Guarda la entrenadora 2 en la BD

        System.out.println("---LISTAR POKEMONS NA POKEDEX---");

        List<Pokedex> pokemonsGuardados = servicioPokedex.listarPokemones();

        if (pokemonsGuardados != null) {
            for (Pokedex p : pokemonsGuardados) {
                System.out.println(p); // Muestra los datos del Pokémon por consola llamando a toString()
            }
        }

        System.out.println("---LISTAR ADESTRADORES---");

        List<Adestrador> adestradoresGuardados = servicioAdestrador.listarAdestradores();

        if (adestradoresGuardados != null) {
            for (Adestrador a : adestradoresGuardados) {
                System.out.println(a);
            }
        }

        System.out.println("---MODIFICAR DOUS ADESTRADORES---");

        Adestrador adestrador1 = servicioAdestrador.leerAdestradorPorNome("Ash Ketchum");

        if (adestrador1 != null) {
            adestrador1.setNome("Ash Ketchum de Pueblo Paleta");
            servicioAdestrador.actualizarAdestrador(adestrador1);
        }

        Adestrador adestrador2 = servicioAdestrador.leerAdestradorPorNome("Misty Waterflower");

        if (adestrador2 != null) {
            adestrador2.setNome("Misty de Ciudad Celeste");
            servicioAdestrador.actualizarAdestrador(adestrador2);
        }

        System.out.println("---MODIFICAR DUAS ENTRADAS NA POKEDEX---");

        Pokedex pokemon1 = servicioPokedex.leerPokemon("Pikachu");

        if (pokemon1 != null) {
            pokemon1.setMisc("Rato eléctrico evolucionado e superpotente");
            servicioPokedex.actualizarPokemon(pokemon1);
        }

        Pokedex pokemon2 = servicioPokedex.leerPokemon("Snorlax");

        if (pokemon2 != null) {
            pokemon2.setMisc("Dormilón enorme que ha despertado");
            servicioPokedex.actualizarPokemon(pokemon2);
        }

        System.out.println("---LISTAR POKEMONS NA POKEDEX (TRAS MODIFICAR)---");

        pokemonsGuardados = servicioPokedex.listarPokemones();

        if (pokemonsGuardados != null) {
            for (Pokedex p : pokemonsGuardados) {
                System.out.println(p);
            }
        }

        System.out.println("---LISTAR ADESTRADORES (TRAS MODIFICAR)---");

        adestradoresGuardados = servicioAdestrador.listarAdestradores();

        if (adestradoresGuardados != null) {
            for (Adestrador a : adestradoresGuardados) {
                System.out.println(a);
            }
        }

        System.out.println("---EXPORTAR A XML---");

        servicioPokedex.exportarPokedexXML("pokemones.xml");

        servicioAdestrador.exportarAdestradoresXML("adestradores.xml");

        System.out.println("---ELIMINAR TÓDO-LOS DATOS DAS TÁBOAS---");

        //servicioPokedex.eliminarTodosPokemon();
        //servicioAdestrador.eliminarTodosAdestradores();


    }
}