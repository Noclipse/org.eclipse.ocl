/*******************************************************************************
 * Copyright (c) 2010, 2020 Willink Transformations and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * This file is copied  from org.eclipse.ui.internal.ide.misc.ContainerContentProvider
 *******************************************************************************/
package org.eclipse.ocl.xtext.base.ui.ripoffs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides content for a tree viewer that shows only containers.
 */
public class ContainerContentProvider implements ITreeContentProvider {
    private boolean showClosedProjects = true;

    /**
     * Creates a new ContainerContentProvider.
     */
    public ContainerContentProvider() {
    }

    /**
     * The visual part that is using this content provider is about
     * to be disposed. Deallocate all allocated SWT resources.
     */
    @Override
	public void dispose() {
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
	@Override
	public Object[] getChildren(Object element) {
        if (element instanceof IWorkspace) {
            // check if closed projects should be shown
            IProject[] allProjects = ((IWorkspace) element).getRoot()
                    .getProjects();
            if (showClosedProjects) {
				return allProjects;
			}

            ArrayList<IProject> accessibleProjects = new ArrayList<IProject>();
            for (int i = 0; i < allProjects.length; i++) {
                if (allProjects[i].isOpen()) {
                    accessibleProjects.add(allProjects[i]);
                }
            }
            return accessibleProjects.toArray();
        } else if (element instanceof IContainer) {
            IContainer container = (IContainer) element;
            if (container.isAccessible()) {
                try {
                    List<IResource> children = new ArrayList<IResource>();
                    IResource[] members = container.members();
                    for (int i = 0; i < members.length; i++) {
                        if (members[i].getType() != IResource.FILE) {
                            children.add(members[i]);
                        }
                    }
                    return children.toArray();
                } catch (CoreException e) {
                    // this should never happen because we call #isAccessible before invoking #members
                }
            }
        }
        return new Object[0];
    }

    /*
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
	public Object[] getElements(Object element) {
        return getChildren(element);
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
	public Object getParent(Object element) {
        if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}
        return null;
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
	public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    /*
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
     */
    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * Specify whether or not to show closed projects in the tree
     * viewer.  Default is to show closed projects.
     *
     * @param show boolean if false, do not show closed projects in the tree
     */
    public void showClosedProjects(boolean show) {
        showClosedProjects = show;
    }

}
