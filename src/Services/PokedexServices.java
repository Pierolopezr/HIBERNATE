package Services;

import Config.HibernateConfig;
import Models.Pokedex;
import org.hibernate.*;
import org.hibernate.query.Query; // versión más moderna para el query

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.util.List;


public class PokedexServices {
    // CREATE
    public void crearPokemon(Pokedex pokemon){
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
          Transaction transaction = session.beginTransaction();
          session.save(pokemon);
          transaction.commit();
        }catch (Exception e){
            System.out.println("Error ao crear pokemon: " + e.getMessage());
        }
    }
    // READ por ID
    public Pokedex leerPokemonPorID(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Pokedex.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao ler o pokemon: " + e.getMessage());
            return null;
        }
    }
    // READ por nome
    public Pokedex leerPokemon(String nombreBuscado) { // Recibe el texto del nombre que quieres buscar y devuelve un objeto Pokedex
    try (Session conexionSesion = HibernateConfig.getSessionFactory().openSession()) { // Abre el canal de lectura con PostgreSQL
        // Construye la consulta HQL buscando en la clase Pokedex donde la variable 'nome' coincida con el comodín
        Query<Pokedex> consultaHql = conexionSesion.createQuery("FROM Pokedex WHERE nome = :nombreParametro", Pokedex.class);

        consultaHql.setParameter("nombreParametro", nombreBuscado);  // Sustituye el comodín ':nombreParametro' por el valor real recibido (ejemplo: "Pikachu")
        return consultaHql.uniqueResult(); // Ejecuta la búsqueda HQL y devuelve el objeto encontrado en la base de datos (o null si no lo encuentra)
    } catch (Exception error) {
        System.out.println("Erro ao ler o pokemon: " + error.getMessage());
        return null; // Retorna null para avisar de que no se pudo leer el registro sin detener la aplicación
    }
    }
    // UPDATE
    public void actualizarPokemon(Pokedex pokemon) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao actualiza-lo pokemon: " + e.getMessage());
        }
    }
    // DELETE
    public void eliminarPokemon(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex pokemon = session.get(Pokedex.class, id);
            if (pokemon!= null) {
                session.delete(pokemon);
            } else {
                System.out.println("non se eliminó o pokemon");
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Non quero eliminar ningun poketroll :v Y.Y: " + e.getMessage());
        }
    }
    public void eliminarTodosPokemon() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> consultaBorradoMasivo = session.createQuery("DELETE FROM Pokedex");
            // Ejecuta el borrado en la BD y guarda en una variable 'int' el número de registros eliminados
            int cantidadFilasBorradas = consultaBorradoMasivo.executeUpdate(); // Ejecuta la orden y devuelve las filas afectadas.
            transaction.commit();
            System.out.println("Elimináronse con éxito " + cantidadFilasBorradas + " pokémons da Pokédex.");
        } catch (Exception e) {
            System.out.println("Non quero eliminar todos los poketroll :v Y.Y: " + e.getMessage());
        }
    }
    // LIST
    public List<Pokedex> listarPokemones() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pokedex", Pokedex.class).getResultList(); // Pokedex en mayuscula porque hibernate lee desde JAVA
        } catch (Exception e) {
            System.out.println("Erro ao lista-los Pokedex: " + e.getMessage());
            return null;
        }
    }
    // XML
    public void exportarPokedexXML(String rutaArchivo) {
        List<Pokedex> listaPokemones = listarPokemones();
        if (listaPokemones == null || listaPokemones.isEmpty()) {
            System.out.println("A lista de Pokémons está baleira, non se pode exportar o XML.");
            return;
        }
        try (FileWriter archivoEscritor = new FileWriter(rutaArchivo)) {
            XMLOutputFactory fabricaXml = XMLOutputFactory.newInstance();
            XMLStreamWriter escritorXml = fabricaXml.createXMLStreamWriter(archivoEscritor);

            escritorXml.writeStartDocument("UTF-8", "1.0");
            escritorXml.writeStartElement("pokedex_lista");

            for (Pokedex pokemonActual : listaPokemones) {
                escritorXml.writeStartElement("pokemon");
                escritorXml.writeAttribute("id", String.valueOf(pokemonActual.getId()));
                escritorXml.writeStartElement("nome");
                escritorXml.writeCharacters(pokemonActual.getNome());
                escritorXml.writeEndElement();
                if (pokemonActual.getPeso() != null) {
                    escritorXml.writeStartElement("peso");
                    escritorXml.writeCharacters(pokemonActual.getPeso().toString());
                    escritorXml.writeEndElement(); }
                if (pokemonActual.getMisc() != null) {
                    escritorXml.writeStartElement("misc");
                    escritorXml.writeCharacters(pokemonActual.getMisc());
                    escritorXml.writeEndElement();
                }
                escritorXml.writeEndElement();
            }

            escritorXml.writeEndElement();
            escritorXml.writeEndDocument();
            escritorXml.flush();
            escritorXml.close();

            System.out.println("Archivo XML exportado correctamente en: " + rutaArchivo);
        } catch (Exception error) {
            System.out.println("Erro ao exportar a XML: " + error.getMessage());
        }
    }
}
