package Services;

import Config.HibernateConfig;
import Models.Adestrador;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.time.temporal.Temporal;
import java.util.List;

public class AdestradorServices {

    // CREATE
    public void crearAdestrador(Adestrador adestrador){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction(); // Las transacciones solo son obligatorias al guardar, modificar o borrarr
            session.save(adestrador);
            transaction.commit();
        }catch (Exception e){
            System.out.println("Error al crear adestrador " + e.getMessage());
        }
    }
    // READ
    public Adestrador leerAdestradorPorID(long id){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            return session.get(Adestrador.class, id);

        }catch (Exception e){
            System.out.println("Error al leer adestrador " + e.getMessage());
            return null;
        }

    }
    public Adestrador leerAdestradorPorNome(String nome){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Query<Adestrador> consultaHql = session.createQuery("FROM Adestrador WHERE nome = :nomeParametro", Adestrador.class);
            consultaHql.setParameter("nomeParametro", nome);

            return consultaHql.uniqueResult();
        }catch (Exception e){
            System.out.println("Error al leer al adestrador " + e.getMessage());
            return null;
        }
    }
    // UPDATE
    public void actualizarAdestrador(Adestrador adestrador){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(adestrador);
            transaction.commit();
        }catch (Exception e){
            System.out.println("Error al actualizar adestrador " + e.getMessage());
        }
    }
    // DELETE
    public void eliminarAdestradorID(Long id){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Adestrador adestrador = session.get(Adestrador.class, id);
            if (adestrador != null){
                session.delete(adestrador);
            }else{
                System.out.println("Non se eliminó o adestrador");
            }
            transaction.commit();
        }catch (Exception e){
            System.out.println("Error al eliminar adestrador " + e.getMessage());
        }
    }
    public void eliminarTodosAdestradores(){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Query<?> consultaBorrado = session.createQuery("DELETE FROM Adestrador");
            int cantidadFilasBorradas = consultaBorrado.executeUpdate();
            transaction.commit();
            System.out.println("Se eliminaron con éxito " + cantidadFilasBorradas + " adestradores.");
        }catch (Exception e){
            System.out.println("Error al eliminar los adestradores");
        }
    }
    // LIST
    public List<Adestrador> listarAdestradores(){
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            return session.createQuery("FROM Adestrador", Adestrador.class).getResultList();
        }catch (Exception e){
            System.out.println("Error al listar adestradorese" + e.getMessage());
        }
        return null;
    }
    // XML
    public void exportarAdestradoresXML(String rutaArchivoXml) {
        List<Adestrador> listaAdestradores = listarAdestradores();
        if (listaAdestradores == null || listaAdestradores.isEmpty()) {
            System.out.println("Non hai adestradores na base de datos para exportar a XML.");
            return;
        } try (FileWriter archivoEscritor = new FileWriter(rutaArchivoXml)) {
            XMLOutputFactory fabricaXml = XMLOutputFactory.newInstance();
            XMLStreamWriter escritorXml = fabricaXml.createXMLStreamWriter(archivoEscritor);
            escritorXml.writeStartDocument("UTF-8", "1.0");
            escritorXml.writeStartElement("adestrador_lista");
            for (Adestrador adestradorActual : listaAdestradores) {
                escritorXml.writeStartElement("adestrador");
                escritorXml.writeAttribute("id", String.valueOf(adestradorActual.getId()));
                escritorXml.writeStartElement("nome");
                escritorXml.writeCharacters(adestradorActual.getNome());
                escritorXml.writeEndElement();
                if(adestradorActual.getNacemento()!= null){
                    escritorXml.writeStartElement("nacemento");
                    escritorXml.writeCharacters(adestradorActual.getNacemento().toString());
                    escritorXml.writeEndElement();
                }
            }
            escritorXml.writeEndElement();
            escritorXml.writeEndDocument();
            escritorXml.flush();
            escritorXml.close();
            System.out.println("Archivo XML exportado correctamente en: " + rutaArchivoXml);
        } catch (Exception error) {
            System.out.println("Erro ao exportar a XML: " + error.getMessage());
        }
    }
}
