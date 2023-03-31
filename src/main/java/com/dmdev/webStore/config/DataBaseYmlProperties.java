package com.dmdev.webStore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring")
public record DataBaseYmlProperties(String url,
                                    String username,
                                    String password,
                                    String driver_class_name,
                                    String dialect,
                                    String show_sql,
                                    String format_sql,
                                    String auto,
                                    String current_session_context_class,
                                    String use_second_level_cache,
                                    String use_query_cache,
                                    String factory_class) {
}
