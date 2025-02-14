package uk.co.sainsburys.springjdbcbatch;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BatchImporter {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public BatchImporter(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  public void individualImport(CustomerData data) {
    int updated = jdbcTemplate.update(
        "INSERT INTO customers (customer_id, post_code, c_type, loading) VALUES (:customerId,:postCode,:type,:loading) ON CONFLICT (customer_id, post_code) DO UPDATE SET customer_id = :customerId, post_code = :postCode, c_type = :type, loading = :loading",
        Map.of("customerId", data.customerId(), "postCode", data.postCode(), "type", data.type(), "loading", data.loading())
        );
  }

  @Transactional
  public void batchImport(List<CustomerData> data) {
    int[] updateCounts = jdbcTemplate.batchUpdate(
        "INSERT INTO customers (customer_id, post_code, c_type, loading) VALUES (:customerId,:postCode,:type,:loading) ON CONFLICT (customer_id, post_code) DO UPDATE SET customer_id = :customerId, post_code = :postCode, c_type = :type, loading = :loading",
        SqlParameterSourceUtils.createBatch(data));

    System.out.println("Updated using # updated :: " + updateCounts.length);
  }
}
