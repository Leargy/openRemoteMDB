package essential_modules.resource_loaders.organizations;

import entities.Organization;
import entities.Organizations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

public class XMLOrganizationsLoader extends OrganizationsLoader {
    @Override
    public final List<Organization> load(InputStream source) {
        FileInputStream fileSource = checkFileSource(source);
        if (fileSource == null) return null;
        Organizations companies = null;
        JAXBContext context = createJAXBContext(Organizations.class);
        if (context == null) return null;
        Unmarshaller parser = createUnmarshallerFromContext(context);
        if (parser == null) return null;
        companies = parseOrganizationsWithUnmarshaller(parser, fileSource);
        if (companies == null) return null;
        try {
            source.close();
        } catch (IOException ioException) {
            System.err.println("Can't close file stream now");
        }
        return companies.getCompanies();
    }

    protected FileInputStream checkFileSource(InputStream source) {
        if (source instanceof FileInputStream)
            return (FileInputStream) source;
        else return null;
    }

    protected Unmarshaller createUnmarshallerFromContext(JAXBContext context) {
        try {
            return context.createUnmarshaller();
        } catch (JAXBException e) {
            return null;
        }
    }

    protected Organizations parseOrganizationsWithUnmarshaller(Unmarshaller parser, FileInputStream source) {
        try {
            return (Organizations) parser.unmarshal(source);
        } catch (ClassCastException | JAXBException e) {
            return null;
        }
    }

    @Override
    public final boolean unload(OutputStream destination, List<Organization> elements) {
        FileOutputStream fileDestination = checkFileDestination(destination);
        if (fileDestination == null) return false;
        Organizations companies = new Organizations(elements);
        JAXBContext context = createJAXBContext(Organizations.class);
        if (context == null) return false;
        Marshaller recorder = createFormattedMarshallerFromContext(context);
        if (recorder == null) return false;
        boolean recordingResult = recordOrganizationsWithMarshaller(recorder, fileDestination, companies);
        try {
            destination.close();
        } catch (IOException ioException) {
            System.err.println("Can't close streams now");
        }
        return recordingResult;
    }

    protected FileOutputStream checkFileDestination(OutputStream destination) {
        if (destination instanceof FileOutputStream)
            return (FileOutputStream) destination;
        else return null;
    }

    protected Marshaller createFormattedMarshallerFromContext(JAXBContext context) {
        try {
            Marshaller recorder = context.createMarshaller();
            recorder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return recorder;
        } catch (JAXBException e) {
            return null;
        }
    }

    protected boolean recordOrganizationsWithMarshaller(Marshaller recorder, FileOutputStream file, Organizations companies) {
        try {
            recorder.marshal(companies, file);
            return true;
        } catch (JAXBException e) {
            return false;
        }
    }

    protected JAXBContext createJAXBContext(Class loadedClass) {
        try {
            return JAXBContext.newInstance(loadedClass);
        } catch (JAXBException e) {
            return null;
        }
    }
}
