package com.vmware.financial.open.banking.repository

import com.vmware.financial.open.banking.domain.Location
import org.springframework.data.repository.CrudRepository

/**
 * @author Gregory Green
 */
interface LocationRepository : CrudRepository<Location,String> {
}