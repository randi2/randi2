/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.utility.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
 
import org.springframework.security.BadCredentialsException;
import org.springframework.security.LockedException;
import org.springframework.security.ui.AbstractProcessingFilter;

import de.randi2.jsf.supportBeans.Randi2;
 
public class LoginErrorPhaseListener implements PhaseListener
{
    private static final long serialVersionUID = -1216620620302322995L;
 
    @Override
    public void beforePhase(final PhaseEvent arg0)
    {
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
                AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
 
        if (e instanceof BadCredentialsException || e instanceof LockedException)
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, null);
            Randi2.showMessage(e.getMessage());
        }
    }
 
    @Override
    public void afterPhase(final PhaseEvent arg0)
    {}
 
    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
 
}
