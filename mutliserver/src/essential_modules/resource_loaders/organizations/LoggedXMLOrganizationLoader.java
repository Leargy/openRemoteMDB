package essential_modules.resource_loaders.organizations;

import entities.Organizations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class LoggedXMLOrganizationLoader extends XMLOrganizationsLoader {
    private final Logger LOG = LoggerFactory.getLogger(XMLOrganizationsLoader.class);

    @Override
    protected FileInputStream checkFileSource(InputStream source) {
        FileInputStream file = super.checkFileSource(source);
        if (file != null)
            LOG.info("File source stream successfully found: " + file.toString());
        else LOG.error("Delivered source isn't a file stream at all: " + file.toString());
        return file;
    }

    @Override
    protected Unmarshaller createUnmarshallerFromContext(JAXBContext context) {
        Unmarshaller parser = super.createUnmarshallerFromContext(context);
        if (parser != null)
            LOG.info("Successful unmarshaller creation from context");
        else LOG.error("Unsuccessful unmarshaller creation from context");
        return parser;
    }

    @Override
    protected Organizations parseOrganizationsWithUnmarshaller(Unmarshaller parser, FileInputStream source) {
        Organizations organizations = super.parseOrganizationsWithUnmarshaller(parser, source);
        if (organizations != null)
            LOG.info("Found several organizations in file");
        else LOG.error("There are no organizations in file");
        return organizations;
    }

    @Override
    protected FileOutputStream checkFileDestination(OutputStream destination) {
        FileOutputStream file = super.checkFileDestination(destination);
        if (file != null)
            LOG.info("File destination stream successfully found: " + file.toString());
        else LOG.error("Delivered destination isn't a file stream at all: " + file.toString());
        return file;
    }

    @Override
    protected Marshaller createFormattedMarshallerFromContext(JAXBContext context) {
        Marshaller recorder = super.createFormattedMarshallerFromContext(context);
        if (recorder != null)
            LOG.info("Successful marshaller creation from context");
        else LOG.error("Unsuccessful marshaller creation from context");
        return recorder;
    }

    @Override
    protected boolean recordOrganizationsWithMarshaller(Marshaller recorder, FileOutputStream file, Organizations companies) {
        boolean result = super.recordOrganizationsWithMarshaller(recorder, file, companies);
        if (result)
            LOG.info("Successful organizations recording to file: " + file.toString());
        else LOG.error("Unsuccessful organizations recording to file: " + file.toString());
        return result;
    }

    @Override
    protected JAXBContext createJAXBContext(Class loadedClass) {
        JAXBContext context = super.createJAXBContext(loadedClass);
        if (context != null)
            LOG.info("Successful context creation for class: " + loadedClass.getSimpleName());
        else LOG.error("Cannot create context from class: " + loadedClass.getSimpleName());
        return context;
    }
}
