
//********************************************************************
//  EmptySetException.java     Authors:
//
//  Represents the situation in which a set is empty.
//********************************************************************

package jss2.exceptions;

@SuppressWarnings("serial")
public class EmptySetException extends RuntimeException
{
   //-----------------------------------------------------------------
   //-----------------------------------------------------------------
   public EmptySetException()
   {
      super ("The set is empty.");
   }

   //-----------------------------------------------------------------
   //-----------------------------------------------------------------
   public EmptySetException (String message)
   {
      super (message);
   }
}