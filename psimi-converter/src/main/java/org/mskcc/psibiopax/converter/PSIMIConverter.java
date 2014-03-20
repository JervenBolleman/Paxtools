/** Copyright (c) 2009 Memorial Sloan-Kettering Cancer Center.
 **
 ** This library is free software; you can redistribute it and/or modify it
 ** under the terms of the GNU Lesser General Public License as published
 ** by the Free Software Foundation; either version 2.1 of the License, or
 ** any later version.
 **
 ** This library is distributed in the hope that it will be useful, but
 ** WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 ** MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 ** documentation provided hereunder is on an "as is" basis, and
 ** Memorial Sloan-Kettering Cancer Center
 ** has no obligations to provide maintenance, support,
 ** updates, enhancements or modifications.  In no event shall
 ** Memorial Sloan-Kettering Cancer Center
 ** be liable to any party for direct, indirect, special,
 ** incidental or consequential damages, including lost profits, arising
 ** out of the use of this software and its documentation, even if
 ** Memorial Sloan-Kettering Cancer Center
 ** has been advised of the possibility of such damage.  See
 ** the GNU Lesser General Public License for more details.
 **
 ** You should have received a copy of the GNU Lesser General Public License
 ** along with this library; if not, write to the Free Software Foundation,
 ** Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 **/
package org.mskcc.psibiopax.converter;

// imports
import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.PsimiXmlReaderException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * An interface which provides methods to convert a PSI-MI file.
 */
public interface PSIMIConverter {

	/**
	 * Converts the PSI-MI inputStream into BioPAX outputStream.
	 * Streams will be closed by the converter.
	 *
	 * Note: for huge models (several Gb), using a byte array output
	 * stream leads to OutOfMemoryError despite there is free heap memory.
	 *
	 * @param inputStream psimi
	 * @param outputStream biopax
	 * @return
	 *
	 * @throws IOException
	 * @throws PsimiXmlReaderException
	 */
	public boolean convert(InputStream inputStream, OutputStream outputStream)
		throws IOException, PsimiXmlReaderException;
	
	/**
	 * Converts the PSI-MITAB inputStream into BioPAX outputStream.
	 * Streams will be closed by the converter.
	 *
	 * @param inputStream psimitab
	 * @param outputStream biopax
	 * @return boolean
	 *
	 * @throws IOException
	 * @throws PsimiTabException
	 */
	public boolean convertTab(InputStream inputStream, OutputStream outputStream)
		throws IOException, PsimiTabException;	

	/**
	 * Converts the PSI interactions from the EntrySet and places into BioPAX output stream.
	 * Stream will be closed by the converter.
	 *
	 * @param entrySet EntrySet
	 * @param outputStream OutputStream
	 * @return boolean
	 */
	public boolean convert(EntrySet entrySet, OutputStream outputStream);
}