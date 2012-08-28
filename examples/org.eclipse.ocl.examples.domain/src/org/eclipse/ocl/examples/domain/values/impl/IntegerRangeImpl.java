/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: IntegerRangeImpl.java,v 1.1 2011/02/11 20:00:28 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.values.impl;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.values.IntegerRange;
import org.eclipse.ocl.examples.domain.values.IntegerValue;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;

/**
 * @generated NOT
 */
class IntegerRangeImpl extends AbstractList<Value> implements IntegerRange
{
	// local iterator class that provides
	// hasNext() and next() methods appropriate
	// for this range set
	class IntegerRangeIterator implements Iterator<Value>
	{
		private IntegerValue curr = null;		// null before first next(), last once finished

//		public Value get() {
//			assert curr != null;
//			return curr;
//		}

		public boolean hasNext() {
			if (curr == null) {
				return first.compareTo(last) <= 0;
			}
			else {
				return !last.equals(curr);
			}
		}

		public IntegerValue next() {
			if (curr == null) {
				curr = first;
			}
			else if (curr.compareTo(last) < 0) {
				try {
					curr = curr.add(getValueFactory().getOne());
				} catch (InvalidValueException e) {
					throw new NoSuchElementException();
				}
			}
			else {
				throw new NoSuchElementException();
			}
			return curr;
		}

		public void remove() {
			throw new UnsupportedOperationException();	// Unimplemented optional operation
		}

		@Override
		public String toString() {
			return String.valueOf(curr);
		}
	}
	
	protected final @NonNull IntegerValue first;		// not null or invalid
	protected final @NonNull IntegerValue last;			// not null or invalid
	protected final @NonNull IntegerValue fullSize;		// zero if empty
	protected final Integer size;						// null if fullSize not an Integer
	
	public IntegerRangeImpl(@NonNull IntegerValue first, @NonNull IntegerValue last) throws InvalidValueException {
		IntegerValue sizeMinusOne = last.subtract(first);
		this.first = first;
		this.last = last;
		if (sizeMinusOne.signum() < 0) {
			this.fullSize = getValueFactory().getZero();
			this.size = 0;
		}
		else {
			this.fullSize = sizeMinusOne.add(getValueFactory().getOne());
			this.size = fullSize.asInteger();
		}
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof IntegerValue)) {
			return false;
		}
		IntegerValue value = (IntegerValue)o;
		return (first.compareTo(value) <= 0) && (value.compareTo(last) <= 0);
	}
	
	@Override
	public @NonNull IntegerValue get(int index) {
		if ((index < 0) || (size == null) || (index >= size)) {
			throw new NoSuchElementException();
//			getValueFactory().throwInvalidValueException("Out of range index {0} for range 0..{1}", index, size);
		}
		try {
			return first.add(getValueFactory().integerValueOf(index));
		} catch (InvalidValueException e) {
			throw new NoSuchElementException();
		}
	}

	public @NonNull IntegerValue getFirst() {
		return first;
	}

	public @NonNull IntegerValue getLast() {
		return last;
	}

	public @NonNull IntegerValue getSize() {
		return fullSize;
	}

	public @NonNull ValueFactory getValueFactory() {
		return first.getValueFactory();
	}

    @Override
	public @NonNull Iterator<Value> iterator() {
		return new IntegerRangeIterator();
	}

	@Override
	public int size() {
		if (size != null) {
			return size;
		}
		else {
			throw new NoSuchElementException();
//			getValueFactory().throwInvalidValueException("Range size {0} cannot be expressed as an int", size);
		}
	}
}