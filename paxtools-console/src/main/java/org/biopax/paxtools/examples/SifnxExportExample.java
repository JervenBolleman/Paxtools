package org.biopax.paxtools.examples;

import java.io.*;

import org.biopax.paxtools.controller.EditorMap;
import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.sif.SimpleInteractionConverter;
import org.biopax.paxtools.io.sif.level2.ComponentRule;
import org.biopax.paxtools.io.sif.level2.ConsecutiveCatalysisRule;
import org.biopax.paxtools.io.sif.level2.ControlRule;
import org.biopax.paxtools.io.sif.level2.ControlsTogetherRule;
import org.biopax.paxtools.io.sif.level2.ParticipatesRule;
import org.biopax.paxtools.io.simpleIO.SimpleEditorMap;
import org.biopax.paxtools.io.simpleIO.SimpleReader;
import org.biopax.paxtools.model.*;

public final class SifnxExportExample {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Please run again providing arguments: "
					+ "input(BioPAX OWL file), edgeOutput, nodeOutput");
			System.exit(-1);
		}

		// import BioPAX from OWL file (auto-detects level)
		BioPAXIOHandler biopaxReader = new SimpleReader();
		Model model = biopaxReader.convertFromOWL(new FileInputStream(args[0]));
		
		SimpleInteractionConverter sic = null;
		if (BioPAXLevel.L2.equals(model.getLevel())) {
			sic = new SimpleInteractionConverter(new ComponentRule(),
					new ConsecutiveCatalysisRule(), new ControlRule(),
					new ControlsTogetherRule(), new ParticipatesRule());
		} else if (BioPAXLevel.L3.equals(model.getLevel())) {
			sic = new SimpleInteractionConverter(
					new org.biopax.paxtools.io.sif.level3.ComponentRule(),
					new org.biopax.paxtools.io.sif.level3.ConsecutiveCatalysisRule(),
					new org.biopax.paxtools.io.sif.level3.ControlRule(),
					new org.biopax.paxtools.io.sif.level3.ControlsTogetherRule(),
					new org.biopax.paxtools.io.sif.level3.ParticipatesRule());
		} else {
			System.err.println("SIF converter does not yet support BioPAX level: " 
					+ model.getLevel());
			System.exit(0);
		}
		
		EditorMap editorMap = new SimpleEditorMap(model.getLevel());
		OutputStream edgeStream = new FileOutputStream(args[1]);
		OutputStream nodeStream = new FileOutputStream(args[2]);
        sic.writeInteractionsInSIFNX(model, edgeStream, nodeStream, 
        		false, editorMap, "NAME","XREF"); // use, e.g., "name","xref" instead - when BioPAX L3
	}

}
